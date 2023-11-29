package AI.AI_prac;

import java.util.*;

public class Water_Jug_aStar {
    static class Node implements Comparable<Node>{
        int j1;
        int j2;
        int gScore;
        int h;
        Node parent;

        public Node(int j1,int j2,int gScore, int h, Node parent){
            this.j1=j1;
            this.j2=j2;
            this.gScore=gScore;
            this.h=h;
            this.parent=parent;
        }

        public int compareTo(Node other){
            int fThis = this.gScore + this.h;
            int fOther = other.gScore + other.h;
            return Integer.compare(fThis, fOther);
        }
    }

    public static int heuristic(int jug1,int jug2,int target){
        return Math.abs(jug1 + jug2 -target );
    }

    public static void print(Node curr,int target){
        ArrayList<Node> list = new ArrayList<>();
        while(curr!=null){
            list.add(curr);
            curr=curr.parent;
        }
        
        for(int i=list.size()-1;i>=0;i--){
            Node curr2 = list.get(i);
            System.out.println(curr2.j1 + " " + curr2.j2);
            if(i==0){
                if(curr2.j1==target){
                    System.out.println(curr2.j1 + " " + 0);
                }else{
                    System.out.println(0 + " " + curr2.j2);
                }
            }
        }
        
    }


    public static boolean aStar(int jug1,int jug2,int target){
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(jug1, jug2, 0, heuristic(0, 0, target),null));
        while(!pq.isEmpty()){
            Node curr = pq.remove();
            if (curr.j1 > jug1 || curr.j2 > jug2)
                continue;
            if(curr.j1==target || curr.j2==target){
                print(curr,target);
                return true;
            }

            pq.add(new Node(jug1, 0, curr.gScore+1, heuristic(jug1, 0, target), curr));
            pq.add(new Node(0, jug2, curr.gScore+1, heuristic(0, jug2, target), curr));

            pq.add(new Node(curr.j1, jug2, curr.gScore+1, heuristic(curr.j1, jug2, target), curr));
            pq.add(new Node(jug1, curr.j2 , curr.gScore+1, heuristic(jug1, curr.j2, target), curr));

            pq.add(new Node(0, curr.j2, curr.gScore+1, heuristic(0, curr.j2, target),curr));
            pq.add(new Node(curr.j1, 0, curr.gScore+1, heuristic(curr.j1, 0, target),curr));

            int emptyJug = jug2-curr.j2;
            int amountTransferred = Math.min(curr.j1,emptyJug);
            int j2 = curr.j2 + amountTransferred;
            int j1 = curr.j1 - amountTransferred;
            pq.add(new Node(j1, j2, curr.gScore+1,heuristic(j1, j2, target),curr));

            emptyJug = jug1 - curr.j1;
            amountTransferred = Math.min(curr.j2,emptyJug);
            j1 = curr.j1+amountTransferred;
            j2 = curr.j2+amountTransferred;
            pq.add(new Node(j1, j2, curr.gScore+1, heuristic(j1, j2, target),curr));
        }
        return false;
    }

    public static void main(String[] args) {
        aStar(4, 3, 2);
    }
}
