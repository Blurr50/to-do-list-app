package model;

public record Task(int id, String title, String description,
		int createdBy, Integer assignedTo, boolean complete) {}