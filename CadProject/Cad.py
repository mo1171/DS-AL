from queue import PriorityQueue
from dataclasses import dataclass
import numpy as np


@dataclass
class Edge:
    source: 'Node'
    destination: 'Node'
    symbol: str
    component_impidace  : int
    Vsource : int = 0  #has voltage source on the branch
    Csource : int = 0 #has current source on the branch
    weight: int = 1

    def __lt__(self, other):
        # if self.weight == other.weight :
        #     return ord(self.symbol) < ord(other.symbol)
        return self.weight < other.weight


class Node:
    def __init__(self, data):
        self.data = data
        self.visited = False
        self.processed = False  # Add processed flag for directed graph
        self.neighbors = set()

    def add_neighbor(self, node):
        self.neighbors.add(node)


class KruskalAlgorithm:
    def __init__(self, graph):
        self.graph = PriorityQueue()
        for edge in graph:
            self.graph.put(edge)
        self.Nodes = self.get_Nodes()
        self.nNodes = len(self.Nodes)

    def run(self):
        spanning_tree = []
        while len(spanning_tree) < self.nNodes - 1:
            edge = self.graph.get()
            self.restore_graph(spanning_tree + [edge])
            if not self.has_cycle():
                spanning_tree.append(edge)

        self.print_minimum_spanning_tree(spanning_tree)
        return spanning_tree, self.nNodes

    def print_minimum_spanning_tree(self, spanning_tree):
        min_weight = sum(edge.weight for edge in spanning_tree)
        for edge in spanning_tree:
            print(f"{edge.symbol } : {edge.source.data} ---> {edge.destination.data}")
        print('--------------------------------')

    def restore_graph(self, temp_tree):
        for Node in self.Nodes:
            Node.visited = False
            Node.neighbors = set()

        for edge in temp_tree:
            edge.source.add_neighbor(edge.destination)

    def get_Nodes(self):
        all_Nodes = [edge.source for edge in self.graph.queue] + \
            [edge.destination for edge in self.graph.queue]
        return list(set(all_Nodes))

    def has_cycle(self):
        def has_cycle_util(node, visited, parent):
            visited.add(node)

            for neighbor in node.neighbors:
                if neighbor not in visited:
                    if has_cycle_util(neighbor, visited, node):
                        return True
                # If the neighbor is visited and not the parent of the current node (exclude direct parent)
                elif neighbor != parent:
                    return True

            return False

        for node in self.Nodes:
            visited = set()
            if has_cycle_util(node, visited, None):
                return True

        return False

        return False

    def is_acyclic_connected_tree(self, spanning_tree):
        # Check for connectedness
        visited = set()

        def dfs(Node):
            visited.add(Node)
            for neighbor in Node.neighbors:
                if neighbor not in visited:
                    dfs(neighbor)

        dfs(spanning_tree[0].source)
        if len(visited) != len(self.Nodes):
            return False

        # Check for acyclic property
        return len(spanning_tree) == self.nNodes - 1


class NetworkTopologyMetrics:
    def __init__(self, graph) -> None:
        self.graph = graph
        self.Tree, self.n_Nodes = KruskalAlgorithm(graph).run()
        self.Tree = sorted(self.Tree, key=lambda x: x.symbol)
        self.CoTree = sorted(
            [edge for edge in graph if edge not in self.Tree], key=lambda x: x.symbol)
        self.A = np.zeros((self.n_Nodes, len(graph)))
        self.Z_B = np.zeros((len(self.graph) , len(self.graph)))
        self.Y_B = np.zeros((len(self.graph),len(self.graph)))
        self.E_B = np.empty((len(self.graph),1))
        self.I_B = np.empty((len(self.graph),1))
        self.make_Matrices_out_of_graph()
        self.A_T, self.A_L = self.divide_A()
        self.C = np.empty((len(self.Tree), len(graph)))
        self.C_L, self.C_T = self.make_C()
        self.B = np.empty((len(self.CoTree), len(graph)))
        self.B_L, self.B_T = self.make_B()

    def make_Matrices_out_of_graph(self):
        for edge in self.graph:
            #making of A (incedence matrix)
            self.A[self.indixing_num(edge.source.data)
                   ][self.indixing_chr(edge.symbol)] = 1
            self.A[self.indixing_num(edge.destination.data)
                   ][self.indixing_chr(edge.symbol)] = -1
            self.Z_B[self.indixing_chr(edge.symbol)
            ][self.indixing_chr(edge.symbol)] = edge.component_impidace # making of Z_B matrix of impedace of branches
            self.E_B[self.indixing_chr(edge.symbol),] = edge.Vsource
            self.I_B[self.indixing_chr(edge.symbol),] = edge.Csource
        for idx in range(len(self.graph)) : 
            self.Y_B[idx][idx] = 1/self.Z_B[idx][idx]
    def make_C(self):
        C_L = np.matmul(np.linalg.inv(self.A_T), self.A_L)
        C_T = np.eye(len(self.Tree))
        for edge, idx in zip(self.Tree, range(len(self.Tree))):
            self.C[:, self.indixing_chr(edge.symbol)] = C_T[:, idx]
        for edge, idx in zip(self.CoTree, range(len(self.CoTree))):
            self.C[:, self.indixing_chr(edge.symbol)] = C_L[:, idx]
        return C_L, C_T

    def make_B(self):
        B_T = -self.C_L.T
        B_L = np.eye(len(self.CoTree))
        for edge, idx in zip(self.Tree, range(len(self.Tree))):
            self.B[:, self.indixing_chr(edge.symbol)] = B_T[:, idx]
        for edge, idx in zip(self.CoTree, range(len(self.CoTree))):
            self.B[:, self.indixing_chr(edge.symbol)] = B_L[:, idx]
        return B_L, B_T

    def divide_A(self):
        A_T = self.A[:, [self.indixing_chr(edge.symbol)
                         for edge in self.Tree]]
        A_L = self.A[:, [self.indixing_chr(edge.symbol)
                         for edge in self.CoTree]]

        return A_T[:len(self.Tree), :], A_L[:len(self.Tree), :]

    def indixing_chr(self, chr):
        return ord(chr)-97

    def indixing_num(self, chr):
        return ord(chr)-49

    def isSquare(self, m):
        return all(len(row) == len(m) for row in m)
    def solveForTieSet(self) : 
        I_L = np.matmul(np.linalg.inv(np.matmul(np.matmul(self.B ,self.Z_B ),self.B.T)),
                        np.subtract(np.matmul(self.B ,self.E_B),np.matmul(np.matmul(self.B, self.Z_B),self.I_B)))
        J_B = np.matmul(self.B.T , I_L)
        V_B = np.subtract(np.matmul(self.Z_B , np.add(J_B , self.I_B)),self.E_B)
        return  np.round(J_B,3) ,np.round( V_B ,3 )
    
    def solveForCutSet(self) : 
        E_N = np.matmul(np.linalg.inv(np.matmul(np.matmul(self.C ,self.Y_B ),self.C.T)),
                        np.subtract(np.matmul(self.C ,self.I_B),np.matmul(np.matmul(self.C, self.Y_B),self.E_B)))
        V_B = np.matmul(self.C.T , E_N)
        J_B = np.subtract(np.matmul(self.Y_B , np.add(V_B , self.E_B)),self.I_B)
        return  np.round(J_B,3) ,np.round( V_B ,3 )
        


if (__name__ == '__main__ '):
    pass

