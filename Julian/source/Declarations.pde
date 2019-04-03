//I generally create these pages so that I can handle my variables

//!!boardWidth and boardHeight declared in Calculator_Project for convinience!!

String equation;
Scanner read = new Scanner(System.in);

//
String numeric = "0123456789.";
String operator = "+-/*^";
String orderStart = "([{";
String orderEnd = ")]}";
String other = "";
String symbol = numeric+operator+orderStart+orderEnd+other;
String operNum = numeric + operator;

int rLevel = 0;

//interface coordinates
int rectNum = 10; //Number of rectangles per row/column
float[][] xLoc = initX();
float[][] yLoc = initY();

//layout
String[][] board = new String[rectNum][rectNum];
int textRow = 8;
Integer[] emptyRows = {7, 9};

//input
StringBuilder input = new StringBuilder();
ArrayList<String> finalExpression = new ArrayList<String>();

boolean solved = false;
Double solution;
