package com.example.realdaydb;

import java.util.ArrayList;

public class Inbox {

	private ArrayList<InboxItem> inboxItems;

	public Inbox() {

		inboxItems = new ArrayList<InboxItem>();

	}

	public void addItem(InboxItem item) {
		inboxItems.add(item);
	}

	public int getSize() {
		return inboxItems.size();
	}

	public InboxItem getItem(int i) {
		return inboxItems.get(i);

	}

}
