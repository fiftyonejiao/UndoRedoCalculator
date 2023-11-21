# UndoRedoCalculator
A simple calculator supports add,subtract,multiply,divide, undo and redo

<img width="955" alt="image" src="https://github.com/fiftyonejiao/UndoRedoCalculator/assets/1438778/a5d4e628-36b4-485c-a96c-61a23c7a1080">

基础计算器类 BasicCalculator 提供了基础计算方法calc, 基于给定的操作符和数值, 计算出累积结果

抽象类 Command 定义了 抽象方法 execute 和 reverse

CalculatorCommand类 继承Command 实现了上述方法
该类的构造函数参数为 BasicCalculator basicCalculator, char curOperator, int num
execute方法的实现为 basicCalculator.calc(curOperator, num);
reverse方法的实现为 basicCalculator.calc(undo(curOperator),num);

UndoRedoCalculator类为主计算器
内部的属性有 BasicCalculator basicCalculator
	List<Command> commands 和 int currentIndex
	其calc方法接收到指定操作符和数值后,构建命令对象 CalculatorCommand(basicCalculator, curOperator, num)

	调用execute方法来执行, 并将命令对象添加到commands列表中记录
	且记录当前的命令位置currentIndex
	当调用undo方法时, 根据索引倒序查找范围内相应的原始CalculatorCommand,调用reverse方法
	当调用redo方法时, 根据索引正序查找范围内相应的原始CalculatorCommand,调用execute方法
