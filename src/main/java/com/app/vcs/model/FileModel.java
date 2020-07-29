package com.app.vcs.model;

import java.util.Arrays;

import javax.persistence.*;

@Entity
@Table(name = "File")
public class FileModel {

  public FileModel() {
        super();
    }

   public FileModel(String name, String type, byte[] fileByte) {
        this.name = name;
        this.type = type;
        this.fileByte = fileByte;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "FILEBYTE", length = 1000)
    private byte[] fileByte;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getFileByte() {
		return fileByte;
	}

	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}

	@Override
	public String toString() {
		return "FileModel [id=" + id + ", name=" + name + ", type=" + type + ", fileByte=" + Arrays.toString(fileByte)
				+ "]";
	}

    
}
