package com.app.vcs.model;

import java.util.Arrays;

import javax.persistence.*;

@Entity
@Table(name = "Files")
public class FileModel {

  public FileModel() {
        super();
    }

   public FileModel(String name, String type, byte[] file) {
        this.name = name;
        this.type = type;
        this.file = file;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "VERSION")
    private int version;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "FILE", length = 1000)
    private byte[] file;

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

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "FileModel [id=" + id + ", name=" + name + ", type=" + type + ", version=" + version + ", file=" + Arrays.toString(file)
				+ "]";
	}

    
}
