from Cad import Node , Edge , KruskalAlgorithm , NetworkTopologyMetrics
import numpy as np 
import unittest
import matplotlib.pyplot as plt
from schemdraw import Drawing
from schemdraw.elements import Resistor
class unitTests(unittest.TestCase) : 
    def print_test_method_name(self):
        print(f"Running test: {self._testMethodName}")
    def setUp(self) -> None:
        self.print_test_method_name()
        self.Node1 = Node("1")
        self.Node2 = Node("2")
        self.Node3 = Node("3")
        self.Node4 = Node('4')
        self.Node5 = Node('5')
        

    def atest_graph1 (self) : 
        
        graph1 = [
            Edge(self.Node4, self.Node1,'d'),
            Edge(self.Node2, self.Node3 , 'c'),
            Edge(self.Node3, self.Node4,'f'),
            Edge(self.Node1, self.Node2 , 'b'),
            Edge(self.Node2, self.Node4,'e'),
            Edge(self.Node3, self.Node1 , 'a')
            
        ]
        # self.assertEqual(len(KruskalAlgorithm(graph=graph1).run()[0]) , 3 ) 
        ob =NetworkTopologyMetrics(graph1)
        print("A : ")
        print(ob.A)
        print('-----------------------------------')
        print("A_T : ")
        print(ob.A_T)
        print('-----------------------------------')
        print("A_L : ")
        print(ob.A_L)
        print('-----------------------------------')
        print("C : ") 
        print(ob.C) 
        print('------------------------------------------------------------------------------')
        print("B : ") 
        print(ob.B )
        print('------------------------------------------------------------------------------')
    def atest_graph2 (self) : 
        graph2 = [
            Edge(self.Node1, self.Node2,'d'),
            Edge(self.Node1, self.Node4,'c'),
            Edge(self.Node1, self.Node3,'b'),
            Edge(self.Node3, self.Node1, 'a'),
            Edge(self.Node4, self.Node3,'e')
            
            
        ]
        # self.assertEqual(len(KruskalAlgorithm(graph=graph2).run()[0]) , 3 ) 

    
    def test_graph3 (self) : 
        graph3 = [
            Edge(self.Node3, self.Node1,'a',component_impidace=5,Vsource=10),
            Edge(self.Node1, self.Node3 , 'b',component_impidace=5),
            Edge(self.Node1, self.Node2 , 'c',component_impidace=10),
            Edge(self.Node2, self.Node3 , 'd',component_impidace=5),
            
            
            
        ]
        ob =NetworkTopologyMetrics(graph3)
        current_matrix , voltage_matrix = ob.solveForTieSet()
#         d = Drawing()

# # Iterate through the edges to draw the circuit
#         for edge in graph3:
        
#             resistor = Resistor(label=f'{edge.component_impidace}Ω')  # Use the impedance value from the edge
#             d.add(resistor)
         
#                 # Show the circuit
#         d.draw()
#         legend_text = "\n".join([f"I={current_value}A, V={voltage_value}V" for current_value, voltage_value in zip(current_matrix, voltage_matrix)])
#         plt.text(0, 0, legend_text, bbox=dict(facecolor='white', alpha=0.8))
#         plt.legend()
#         plt.show()
        
    def atest_graph4 (self) : 
        graph4 = [
            Edge(self.Node1, self.Node2 , 'b'),
            Edge(self.Node2, self.Node4 , 'e'),
            Edge(self.Node2, self.Node3 , 'c'),
            Edge(self.Node3, self.Node1, 'a'),
            Edge(self.Node3, self.Node4 , 'f'),
            Edge(self.Node4, self.Node1 , 'd'),
            Edge(self.Node4, self.Node3 , 'g'),
            
            
            
        ]
        
        self.assertEqual(len(KruskalAlgorithm(graph=graph4).run()[0]) , 3 ) 

    def atest_graph5(self) :
        # Example graph for directed edges
        
        graph5 = [
            Edge(self.Node1, self.Node2 , 'a'),
            Edge(self.Node4, self.Node1, 'd'),
            Edge(self.Node5, self.Node1,'g'),
            Edge(self.Node2, self.Node5,'e'),
            Edge(self.Node2, self.Node3, 'b'),
            Edge(self.Node3, self.Node4, 'c'),
            Edge(self.Node5, self.Node3,'h'),
            Edge(self.Node5, self.Node4,'f'),
        ]
        
        self.assertEqual(len(KruskalAlgorithm(graph=graph5).run()[0]) , 4 )

    def atest_graph6(self) : 
        # Example graph for directed edges
        
        graph6 = [
            Edge(self.Node1, self.Node2 , 'c'),
            Edge(self.Node2, self.Node1, 'f'),
            Edge(self.Node2, self.Node3,'e'),
            Edge(self.Node1, self.Node4,'a'),
            Edge(self.Node2, self.Node4 , 'b'),
            Edge(self.Node3, self.Node4,'d'),
        ]
        
        self.assertEqual(len(KruskalAlgorithm(graph=graph6).run()[0]) , 3 ) 

    def atest_graph7(self) : 
        # Example graph for directed edges
        
        graph7 = [
            Edge(self.Node1, self.Node2,'d'),
            Edge(self.Node1, self.Node4,'b'),
            Edge(self.Node3, self.Node1,'a'),
            Edge(self.Node2, self.Node3,'e'),
            Edge(self.Node2, self.Node4,'f'),
            Edge(self.Node4, self.Node3,'c'),
        ]
        
        self.assertEqual(len(KruskalAlgorithm(graph=graph7).run()[0]) , 3 ) 
    def atest_graph8(self) : 
        # Example graph for directed edges
        
        graph8 = [
            Edge(self.Node1, self.Node2,'b',component_impidace=2),
            Edge(self.Node1, self.Node3,'a',component_impidace=2,Csource=10),
            Edge(self.Node3, self.Node2,'d'),
            Edge(self.Node2, self.Node3,'c'),
            
        ]
        
        self.assertEqual(len(KruskalAlgorithm(graph=graph8).run()[0]) , 2 )
    def atest_graph9(self) : 
        # Example graph for directed edges
        
        graph9 = [
            Edge(self.Node2, self.Node1,'a'),
            Edge(self.Node1, self.Node3,'c'),
            Edge(self.Node3, self.Node2,'d'),
            Edge(self.Node3, self.Node4,'e'),
            Edge(self.Node4, self.Node1,'b'),
            
        ]
        
        self.assertEqual(len(KruskalAlgorithm(graph=graph9).run()[0]) , 3 )
    def atest_graphlast(self) : 
        # Example graph for directed edges
        
        graph11 = [
            Edge(self.Node1, self.Node2,'b'),
            Edge(self.Node1, self.Node3,'c'),
            Edge(self.Node3, self.Node2,'e'),
            Edge(self.Node2, self.Node1,'d'),
            Edge(self.Node3, self.Node1,'a'),

            
        ]
        
        self.assertEqual(len(KruskalAlgorithm(graph=graph11).run()[0]) , 2 )
      
if (__name__ == '__main__') : 
    unittest.main() 