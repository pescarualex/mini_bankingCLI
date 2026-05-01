package model;

import enums.Role;
import enums.Status;

public class Admin extends Client{

    public Admin(){
        setRole(Role.ADMIN);
        setStatus(Status.APPROVED);
    }
}




