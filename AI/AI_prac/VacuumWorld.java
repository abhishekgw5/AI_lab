package AI.AI_prac;

import java.util.Random;

public class VacuumWorld {
    static final int size = 2;
    static final int clean = 0;
    static final int dirty = 1;

    public static void main(String[] args) {
        int [][] env = new int[size][size];

        initializeEnv(env);
        System.out.println("Initial state : "); 
        printEnv(env);

        VacuumCleanerAgent agent = new VacuumCleanerAgent();
        agent.clean(env);

        System.out.println("Final state : ");
        printEnv(env);

    }

    static void initializeEnv(int [][] env){
        Random random = new Random();
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                env[i][j] = random.nextInt(2);
            }
        }
    }

    static void printEnv(int [][] env){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                System.out.print(env[i][j] == clean ? "C\t" : "D\t");
            }
            System.out.println();
        }
    }
}

class VacuumCleanerAgent{
    void clean(int [][] env){
        for(int i = 0; i<VacuumWorld.size; i++){
            for(int j = 0; j<VacuumWorld.size; j++){
                if(env[i][j] == VacuumWorld.dirty){
                    System.out.println("Cleaning square (" + i + ", " + j + ")");
                    env[i][j] = VacuumWorld.clean;
                }
            }
        }
    }
}