package io.hilal.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolExample {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        ToughQuestion toughQuestion = new ToughQuestion(128);
        long result = forkJoinPool.invoke(toughQuestion);
        System.out.println(result);
        //Recursive action does not return any  values so we are going to use recursive task here
    }

    public static class ToughQuestion extends RecursiveTask<Long> {
        long currentTask;

        public ToughQuestion(long currentTask) {
            this.currentTask = currentTask;
        }

        @Override
        protected Long compute() {
            if(this.currentTask>16){
                List<ToughQuestion> list = new ArrayList<ToughQuestion>();
                list = createSubtasks();
                long result = 0;
                for (ToughQuestion task : list){
                    task.fork();
                }
                for (ToughQuestion task : list) {
                    result += task.join();
                }
                return result;
            }
            else{
                return this.currentTask*2;
            }
        }
        private List<ToughQuestion> createSubtasks() {
            System.out.println("here "+ this.currentTask);
            List<ToughQuestion> subtasks =
                    new ArrayList<ToughQuestion>();

            ToughQuestion subtask1 = new ToughQuestion(this.currentTask / 2);
            ToughQuestion subtask2 = new ToughQuestion(this.currentTask / 2);

            subtasks.add(subtask1);
            subtasks.add(subtask2);

            return subtasks;
        }
    }
}
