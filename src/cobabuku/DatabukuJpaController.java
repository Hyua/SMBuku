/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobabuku;

import cobabuku.exceptions.NonexistentEntityException;
import cobabuku.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author asus
 */
public class DatabukuJpaController implements Serializable {

    public DatabukuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Databuku databuku) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(databuku);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDatabuku(databuku.getId()) != null) {
                throw new PreexistingEntityException("Databuku " + databuku + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Databuku databuku) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            databuku = em.merge(databuku);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = databuku.getId();
                if (findDatabuku(id) == null) {
                    throw new NonexistentEntityException("The databuku with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Databuku databuku;
            try {
                databuku = em.getReference(Databuku.class, id);
                databuku.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The databuku with id " + id + " no longer exists.", enfe);
            }
            em.remove(databuku);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Databuku> findDatabukuEntities() {
        return findDatabukuEntities(true, -1, -1);
    }

    public List<Databuku> findDatabukuEntities(int maxResults, int firstResult) {
        return findDatabukuEntities(false, maxResults, firstResult);
    }

    private List<Databuku> findDatabukuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Databuku.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Databuku findDatabuku(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Databuku.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatabukuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Databuku> rt = cq.from(Databuku.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
