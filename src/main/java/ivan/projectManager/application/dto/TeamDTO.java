package ivan.projectManager.application.dto;

public class TeamDTO {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public TeamDTO setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TeamDTO setName(String name) {
        this.name = name;
        return this;
    }
}
