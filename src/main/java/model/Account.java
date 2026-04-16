package model;


public class Account {
    private int id;
    private long amountOfMoney;
    private int clientId;

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getClientId(){
        return clientId;
    }

    public void setClientId(int clientId){
        this.clientId = clientId;
    }

    public long getAmountOfMoney(){
        return amountOfMoney;
    }

    public void setAmountOfMoney(long money){
        this.amountOfMoney = money;
    }


    @Override
    public String toString() {
        return "Account{" +
                "ID=" + id +
                ", client_ID=" + clientId +
                ", amountOfMoney=" + amountOfMoney +
                '}';
    }
}
