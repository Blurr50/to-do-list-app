package model;

/**
 * A task as shown in the app.
 * createdBy / assignedTo are user ids; creatorName / assigneeName are the matching usernames
 * (assigneeName is null when the task is unassigned).
 * status is TODO, IN_PROGRESS or DONE. dueDate is an ISO date such as "2026-12-31", or null.
 * priority: 1 = High, 2 = Medium, 3 = Low.
 */
public record Task(int id, String title, String description, int createdBy, String creatorName,
		Integer assignedTo, String assigneeName, String status, String dueDate, int priority) {

	public static final String TODO = "TODO";
	public static final String IN_PROGRESS = "IN_PROGRESS";
	public static final String DONE = "DONE";

	public static final int HIGH = 1;
	public static final int MEDIUM = 2;
	public static final int LOW = 3;

	public boolean complete() {
		return DONE.equals(status);
	}

	public static String statusLabel(String status) {
		if (IN_PROGRESS.equals(status)) {
			return "In progress";
		}
		return DONE.equals(status) ? "Completed" : "To do";
	}

	/** Turns "To do" / "In progress" / "Completed" back into the stored status. */
	public static String statusFromLabel(String label) {
		if ("In progress".equals(label)) {
			return IN_PROGRESS;
		}
		return "Completed".equals(label) ? DONE : TODO;
	}

	public static String priorityLabel(int priority) {
		switch (priority) {
		case HIGH:
			return "High";
		case LOW:
			return "Low";
		default:
			return "Medium";
		}
	}

	public static int priorityFromLabel(String label) {
		if ("High".equals(label)) {
			return HIGH;
		}
		return "Low".equals(label) ? LOW : MEDIUM;
	}
}
