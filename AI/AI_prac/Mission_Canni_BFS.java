package AI.AI_prac;

import java.util.*;

// Question: In the missionaries and cannibals problem, 
// three missionaries and three cannibals must cross a river using a boat 
// which can carry at most two people, under the constraint that, 
// for both banks, if there are missionaries present on the bank, 
// they cannot be outnumbered by cannibals (if they were, the cannibals would eat the missionaries). 
// The boat cannot cross the river by itself with no people on board.

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

enum Position {
    LEFT, RIGHT
}

class State {
    private int cannibalLeft;
    private int missionaryLeft;
    private Position boat;
    private int cannibalRight;
    private int missionaryRight;
    private State parentState;

    public State(int cannibalLeft, int missionaryLeft, Position boat, int cannibalRight, int missionaryRight) {
        this.cannibalLeft = cannibalLeft;
        this.missionaryLeft = missionaryLeft;
        this.boat = boat;
        this.cannibalRight = cannibalRight;
        this.missionaryRight = missionaryRight;
    }

    public boolean isValid() {
        if ((missionaryLeft >= 0 && missionaryRight >= 0 && cannibalLeft >= 0 && cannibalRight >= 0)
                && (missionaryLeft == 0 || missionaryLeft >= cannibalLeft)
                && (missionaryRight == 0 || missionaryRight >= cannibalRight)) {
            return true;
        }
        return false;
    }

    public boolean isGoal() {
        return cannibalLeft == 0 && missionaryLeft == 0;
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

    public void setParentState(State parentState) {
        this.parentState = parentState;
    }

    private void addSuccessor(ArrayList<State> successors, State newState) {
        if (newState.isValid()) {
            newState.setParentState(this);
            successors.add(newState);
        }
    }

    public void printPath() {
        if (parentState != null) {
            parentState.printPath();
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Missionaries and Cannibals - [Left: " + cannibalLeft + "C, " + missionaryLeft + "M], [Boat: " + boat
                + "], [Right: " + cannibalRight + "C, " + missionaryRight + "M]";
    }
}

public class Mission_Canni_BFS {
    public static void main(String[] args) {
        State initialState = new State(3, 3, Position.LEFT, 0, 0);
        State solution = solve(initialState);
        if (solution != null) {
            System.out.println("Solution found:");
            solution.printPath();
        } else {
            System.out.println("No solution found.");
        }
    }

    public static State solve(State initialState) {
        Queue<State> queue = new LinkedList<>();
        queue.add(initialState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (currentState.isGoal()) {
                return currentState;
            }
            ArrayList<State> successors = currentState.generateSuccessors();
            for (State successor : successors) {
                queue.add(successor);
            }
        }
        return null;
    }
}
