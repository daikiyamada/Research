package Input;

public class MyNode {
    public String Node_ID;
    public int Node_Num;

    public MyNode(String Node_ID,int Node_num){
        this.Node_ID = Node_ID;
        this.Node_Num = Node_num;
    }
    @Override
    public String toString(){
        return Node_ID+Node_Num;
    }
}
