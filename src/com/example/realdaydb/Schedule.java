package com.example.realdaydb;

import java.util.ArrayList;
import java.util.Date;

public class Schedule implements Collection {
	private ArrayList<Item> items;
	private Date date;

	public Schedule(Date specificDate) {
		date = specificDate;
		items = new ArrayList<Item>();

	}

	public Date getDate() {
		return date;

	}

	public void addItem(Item item) {
		items.add(item);
	}

	public int getSize() {
		return items.size();
	}

	public Item getItem(int i) {
		return items.get(i);

	}

	public Iterator iterator() {
		// TODO Auto-generated method stub
		return new MyIterator(this);
	}

	public Object get(int i) {
		// TODO Auto-generated method stub
		return items.get(i);
	}

	public int size() {
		// TODO Auto-generated method stub
		return items.size();
	}

}
