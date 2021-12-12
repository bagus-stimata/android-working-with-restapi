package com.example.restspringclientwithbasicsecurity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Integer.class)
public class Todo {

    private int ID = 0;

    private String description = "";

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    private LocalDate dateFrom = LocalDate.now();
//
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    private LocalDate dateTo = LocalDate.now();

//    @JsonManagedReference
    private Person personBean;

    @JsonBackReference
    private List<Aktifitas> todos;

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }





    public Person getPersonBean() {
        return personBean;
    }

    public void setPersonBean(Person personBean) {
        this.personBean = personBean;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Todo other = (Todo) obj;
        if (ID != other.ID)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Todo [description=" + description + "]";
    }

    



    /**
     * @return List<Aktifitas> return the todos
     */
    public List<Aktifitas> getTodos() {
        return todos;
    }

    /**
     * @param todos the todos to set
     */
    public void setTodos(List<Aktifitas> todos) {
        this.todos = todos;
    }

}