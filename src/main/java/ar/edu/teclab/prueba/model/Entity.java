package ar.edu.teclab.prueba.model;

import java.util.Objects;

public abstract class Entity {
    protected Long id;

    protected Entity(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity entity = (Entity) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }
}
