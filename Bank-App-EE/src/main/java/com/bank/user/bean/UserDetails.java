package com.bank.user.bean;

public class UserDetails {

	private long accountNumber;
	private String name;
	private String bankBranch;
	private String accountType;
	private double balance;
	private long contactNumber;

	public UserDetails(long accountNumber, String name, String bankBranch, String accountType, double balance,
			long contactNumber) {
		super();
		this.accountNumber = accountNumber;
		this.name = name;
		this.bankBranch = bankBranch;
		this.accountType = accountType;
		this.balance = balance;
		this.contactNumber = contactNumber;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}

}
