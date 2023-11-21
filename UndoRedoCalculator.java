  /**
     * 一个计算器类（Calculator）
     * 可以实现两个数的加、减、乘、除运算
     * 并可以进行undo和redo操作 (undo和redo 就是撤销和重做的操作)
     * 可在实现功能的基础上发挥最优设计
     */

    class RunMain {
        public static UndoRedoCalculator calculator = new UndoRedoCalculator();

        public static void main(String[] args) {
            calculator.calc('+', 100);//加法
            calculator.calc('-', 50);//减法
            calculator.calc('*', 10);//乘法
            calculator.calc('/', 2);//除法
            calculator.undo(4);//撤销前四个步骤
            calculator.redo(3);//再次执行三个步骤
            //再次执行
            calculator.calc('+', 2);//加法
        }
    }

public class UndoRedoCalculator {
        public BasicCalculator basicCalculator = new BasicCalculator();
        public List<Command> commands = new ArrayList<Command>();
        public int currentIndex = 0;

        //计算
        public void calc(char curOperator, int num) {
            Command command = new CalculatorCommand(basicCalculator, curOperator, num);
            command.execute();
            commands.add(command);
            currentIndex++;
        }

        //重做
        public void redo(int levels) {
            System.out.println("Redo" + levels);
            for (int i = 0; i < levels; i++)
                if (currentIndex < commands.size() - 1) {
                    ((Command) commands.get(currentIndex++)).execute();
                }else {
                    System.out.println("没有可redo的数据!");
                }
        }

        //撤销
        public void undo(int levels) {
            System.out.println("Undo" + levels);
            for (int i = 0; i < levels; i++)
                if (currentIndex > 0) {
                    int index = --currentIndex;
                    Command command= (Command)commands.get(index);
                    command.reverse();
                }else {
                    System.out.println("没有可undo的数据!");
                }
            }
        }

    /**
     * 基本计算器
     *
     */
    class BasicCalculator {
        private int total = 0;
        public void calc(char curOperator, int num) {
            int before = total;
            switch (curOperator) {
                case '+':
                    total += num;
                    break;
                case '-':
                    total -= num;
                    break;
                case '*':
                    total *= num;
                    break;
                case '/':
                    total /= num;
                    break;
            }
            System.out.println(before +"" + curOperator +num+"=" + total);
        }
    }

    abstract class Command {
        abstract public void execute();
        abstract public void reverse();
    }

    class CalculatorCommand extends Command {
        char curOperator;
        int num;
        BasicCalculator basicCalculator;

        public CalculatorCommand(BasicCalculator basicCalculator, char curOperator, int num) {
            this.basicCalculator = basicCalculator;
            this.curOperator = curOperator;
            this.num = num;
        }

        public char getCurOperator() {
            return curOperator;
        }

        public void setCurOperator(char curOperator) {
            this.curOperator = curOperator;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public BasicCalculator getCalculator() {
            return basicCalculator;
        }

        public void setCalculator(BasicCalculator basicCalculator) {
            this.basicCalculator = basicCalculator;
        }

        @Override
        public void execute() {
            basicCalculator.calc(curOperator, num);
        }

        @Override
        public void reverse() {
            basicCalculator.calc(undo(curOperator), num);
        }

        private char undo(char op) {
            char undo = ' ';
            switch (op) {
                case '+':
                    undo = '-';
                    break;
                case '-':
                    undo = '+';
                    break;
                case '*':
                    undo = '/';
                    break;
                case '/':
                    undo = '*';
                    break;
                }
            return undo;
        }
    }
