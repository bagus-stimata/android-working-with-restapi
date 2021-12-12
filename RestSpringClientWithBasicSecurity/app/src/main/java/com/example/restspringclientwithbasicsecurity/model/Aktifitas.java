package com.example.restspringclientwithbasicsecurity.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Integer.class)
public class Aktifitas {

    private int id = 0;

    private String description = "";

//    @JsonManagedReference
    private Todo todoBean;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        Aktifitas other = (Aktifitas) obj;
        if (id != other.id)
            return false;
        return true;
    }




 


    /**
     * @return int return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

   

    /**
     * @return Todo return the todoBean
     */
    public Todo getTodoBean() {
        return todoBean;
    }

    /**
     * @param todoBean the todoBean to set
     */
    public void setTodoBean(Todo todoBean) {
        this.todoBean = todoBean;
    }

}