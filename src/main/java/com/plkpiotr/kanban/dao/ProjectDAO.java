package com.plkpiotr.kanban.dao;

import com.plkpiotr.kanban.api.Project;
import com.plkpiotr.kanban.api.Task;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * Provides "Data Access Object" for "Project" class.
 */
public class ProjectDAO {
    private EntityManager entityManager;

    /**
     * Creates a project through given manager of entities.
     *
     * @param entityManager Manager of entities for project.
     */
    public ProjectDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Gets tasks in a project by category.
     *
     * @param category One of the category task's todo, doing, done, archived.
     * @param idProject Name of reviewed project
     */
    public List<Task> getListOfTasks(String category, int idProject) {
        List listOfProjects = this.entityManager.createNativeQuery("SELECT e.avatar, e.name, e.surname, t.content  " +
                "FROM employees AS e, tasks AS t WHERE t.category = ':kind' AND t.\"idProject\"= :idProject AND e" +
                ".id = t" +
                ".\"idEmployee\"")
                .setParameter("kind", category)
                .setParameter("idProject",idProject)
                .getResultList();
        return listOfProjects;
    }

    /**
     * Inserts a project to database.
     *
     * @param project Project object waiting for inserting to database.
     */
    public boolean insertProject(Project project) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.persist(project);
            entityTransaction.commit();
            return true;
        } catch (Exception exception) {
            entityTransaction.rollback();
            exception.printStackTrace();
            return false;
        }
    }
}
