package model;

import enums.Role;
import enums.Status;

public class Admin {
        private int id;
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private Role role;
        private Status status;

        public int getId(){
            return id;
        }

        public void setId(int id){
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username){
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }


        @Override
        public String toString() {
            return "Admin{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", username='" + username + '\'' +
                    ". role=" + role.name() + '\'' +
                    ", status=" + status.name() + '\'' +
                    '}';
        }
    }




