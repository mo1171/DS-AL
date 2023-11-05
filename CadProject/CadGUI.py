import tkinter as tk
from tkinter import simpledialog
class State:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.diameter = 50

    def draw(self):
        canvas.create_oval(self.x, self.y, self.x + self.diameter, self.y + self.diameter, outline="black", fill="blue")

    def is_inside(self, point_x, point_y):
        return self.x <= point_x <= self.x + self.diameter and self.y <= point_y <= self.y + self.diameter

    def get_center(self):
        return self.x + self.diameter / 2, self.y + self.diameter / 2

class Transition:
    def __init__(self, from_state, to_state, label):
        self.from_state = from_state
        self.to_state = to_state
        self.label = label

    def draw(self):
        from_x, from_y = self.from_state.get_center()
        to_x, to_y = self.to_state.get_center()
        canvas.create_line(from_x, from_y, to_x, to_y, fill="black")
        mid_x, mid_y = (from_x + to_x) / 2, (from_y + to_y) / 2
        canvas.create_text(mid_x, mid_y, text=self.label)

def create_state(event):
    new_state = State(event.x, event.y)
    states.append(new_state)
    new_state.draw()

def create_transition(event):
    for state in states:
        if state.is_inside(event.x, event.y):
            global first_state, second_state
            if first_state is None:
                first_state = state
            else:
                second_state = state
                label = simpledialog.askstring("Input", "Enter transition label:")
                if label:
                    new_transition = Transition(first_state, second_state, label)
                    transitions.append(new_transition)
                    new_transition.draw()
                first_state, second_state = None, None
            break

def toggle_state_mode():
    global create_state_mode, first_state, second_state
    create_state_mode = True
    first_state, second_state = None, None

def toggle_transition_mode():
    global create_state_mode, first_state, second_state
    create_state_mode = False
    first_state, second_state = None, None

def create_state(event=None):
    new_state = State(event.x, event.y)
    states.append(new_state)
    new_state.draw()

def create_transition(event):
    for state in states:
        if state.is_inside(event.x, event.y):
            global first_state, second_state
            if first_state is None:
                first_state = state
            else:
                second_state = state
                label = simpledialog.askstring("Input", "Enter transition label:")
                if label:
                    new_transition = Transition(first_state, second_state, label)
                    transitions.append(new_transition)
                    new_transition.draw()
                first_state, second_state = None, None
            break

if __name__ == "__main__":
    root = tk.Tk()
    root.title("Automata Editor")
    root.geometry("600x400")

    canvas = tk.Canvas(root, bg="white", width=600, height=400)
    canvas.pack()

    states = []
    transitions = []
    create_state_mode = True
    first_state, second_state = None, None

    canvas.bind("<Button-1>", create_state if create_state_mode else create_transition)

    menu_bar = tk.Menu(root)
    mode_menu = tk.Menu(menu_bar, tearoff=0)
    mode_menu.add_command(label="Create State", command=create_state)
    mode_menu.add_command(label="Create Transition", command=toggle_transition_mode)
    menu_bar.add_cascade(label="Mode", menu=mode_menu)
    root.config(menu=menu_bar)

    root.mainloop()

