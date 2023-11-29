package AI.AI_prac;

import java.util.*;

enum Position {
    LEFT, RIGHT
}

class State implements Comparable<State> {
    int cannibalLeft;
    int missionaryLeft;
    Position boat;
    int cannibalRight;
    int missionaryRight;
    State parentState;

    public State(int cannibalLeft, int missionaryLeft, Position boat, int cannibalRight, int missionaryRight) {
        this.cannibalLeft = cannibalLeft;
        this.missionaryLeft = missionaryLeft;
        this.boat = boat;
        this.cannibalRight = cannibalRight;
        this.missionaryRight = missionaryRight;
    }

    private int heuristic() {
        // A simple heuristic: add missionaries and cannibals on the left side
        return Math.abs(cannibalLeft + missionaryLeft)/2;
    }

    public int getCost() {
        // The cost to reach this state from the initial state
        if (parentState == null) {
            return 0;
        }
        return parentState.getCost() + 1;
    }

    public int getHeuristicCost() {
        // The estimated cost from this state to the goal state
        return heuristic();
    }

    public int getTotalCost() {
        // The total cost used by A* (f(n) = g(n) + h(n))
        return getCost() + getHeuristicCost();
    }

    public int compareTo(State other) {
        int fThis = this.getTotalCost();
        int fOther = other.getTotalCost();
        return Integer.compare(fThis, fOther);
    }

    public void setParentState(State parentState) {
        this.parentState = parentState;
    }

    public boolean isValid() {
        return (missionaryLeft >= 0 && missionaryRight >= 0 && cannibalLeft >= 0 && cannibalRight >= 0)
                && (missionaryLeft == 0 || missionaryLeft >= cannibalLeft)
                && (missionaryRight == 0 || missionaryRight >= cannibalRight);
    }

    public boolean isGoal() {
        return cannibalLeft == 0 && missionaryLeft == 0;
    }

    public void addSuccessor(ArrayList<State> successors, State newState) {
        if (newState.isValid()) {
            newState.setParentState(this);
            successors.add(newState);
        }
    }

    public ArrayList<State> generateSuccessors() {
        ArrayList<State> successors = new ArrayList<>();

        if (boat == Position.LEFT) {
            addSuccessor(successors, new State(cannibalLeft, missionaryLeft - 2, Position.RIGHT,
                    cannibalRight, missionaryRight + 2)); // Two missionaries cross left to right.
            addSuccessor(successors, new State(cannibalLeft - 2, missionaryLeft, Position.RIGHT,
                    cannibalRight + 2, missionaryRight)); // Two cannibals cross left to right.
            addSuccessor(successors, new State(cannibalLeft - 1, missionaryLeft - 1, Position.RIGHT,
                    cannibalRight + 1, missionaryRight + 1)); // One missionary and one cannibal cross left to right.
            addSuccessor(successors, new State(cannibalLeft, missionaryLeft - 1, Position.RIGHT,
                    cannibalRight, missionaryRight + 1)); // One missionary crosses left to right.
            addSuccessor(successors, new State(cannibalLeft - 1, missionaryLeft, Position.RIGHT,
                    cannibalRight + 1, missionaryRight)); // One cannibal crosses left to right.
        } else {
            addSuccessor(successors, new State(cannibalLeft, missionaryLeft + 2, Position.LEFT,
                    cannibalRight, missionaryRight - 2)); // Two missionaries cross right to left.
            addSuccessor(successors, new State(cannibalLeft + 2, missionaryLeft, Position.LEFT,
                    cannibalRight - 2, missionaryRight)); // Two cannibals cross right to left.
            addSuccessor(successors, new State(cannibalLeft + 1, missionaryLeft + 1, Position.LEFT,
                    cannibalRight - 1, missionaryRight - 1)); // One missionary and one cannibal cross right to left.
            addSuccessor(successors, new State(cannibalLeft, missionaryLeft + 1, Position.LEFT,
                    cannibalRight, missionaryRight - 1)); // One missionary crosses right to left.
            addSuccessor(successors, new State(cannibalLeft + 1, missionaryLeft, Position.LEFT,
                    cannibalRight - 1, missionaryRight)); // One cannibal crosses right to left.
        }
        return successors;
    }

    public void printPath() {
        if (parentState != null) {
            parentState.printPath();
            System.out.println("Missionaries and Cannibals - [Left: " + cannibalLeft + "C, " + missionaryLeft + "M], [Boat: " + boat
                    + "], [Right: " + cannibalRight + "C, " + missionaryRight + "M]");
        }
    }
}

public class Miss_Can_aStar {
    public static State aStar(State initial) {
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(initial);
        while (!pq.isEmpty()) {
            State curr = pq.remove();
            if (curr.isGoal()) {
                return curr;
            }

            ArrayList<State> successors = curr.generateSuccessors();
            for (State successor : successors) {
                pq.add(successor);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        State initial = new State(3, 3, Position.LEFT, 0, 0);
        State solution = aStar(initial);
        if (solution != null) {
            System.out.println("Solution found:");
            solution.printPath();
        } else {
            System.out.println("No solution found.");
        }
    }
}
