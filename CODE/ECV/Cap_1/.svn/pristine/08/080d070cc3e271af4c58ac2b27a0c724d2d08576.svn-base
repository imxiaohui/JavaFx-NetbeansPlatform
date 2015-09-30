/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 * @author gail
 */
public final class Picture implements Serializable {

    private Long id;
    private String filename = "";
    private String format = "";
    private BufferedImage image = null;
    private Person person;
    private final Object lock = new Object();

    public Picture(Long id) {
        this.id = id;
    }

    public Picture() {
    }

    public Long getId() {
        return id;
    }

    public String getFilename() {
        synchronized (lock) {
            return filename;
        }
    }

    public void setFilename(String filename) {
        synchronized (lock) {
            this.filename = filename;
            setFormat(new StringBuilder(filename).substring(filename.length() - 3).toString());
        }
    }

    public String getFormat() {
        synchronized (lock) {
            return format;
        }
    }

    public void setFormat(String format) {
        synchronized (lock) {
            this.format = format;
        }
    }

    public BufferedImage getImage() {
        synchronized (lock) {
            return image;
        }
    }

    public void setImage(BufferedImage image) {
        synchronized (lock) {
            this.image = image;
        }
    }

    public Person getPerson() {
        synchronized (lock) {
            return person;
        }
    }

    public void setPerson(Person person) {
        synchronized (lock) {
            this.person = person;
        }
    }

    @Override
    public boolean equals(Object o) {
        synchronized (lock) {
            if (o == null) {
                return false;
            }
            if (getClass() != o.getClass()) {
                return false;
            }
        }
        return this.getId().equals(((Picture) o).getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFilename()).append(" for ").append(getPerson());
        return sb.toString();
    }
}
