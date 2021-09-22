package com.bank.user.dao;

import java.sql.ResultSet;

import com.bank.user.bean.UserDetails;

public interface UserDao {

	public int createAccount(UserDetails user);

	public int deposit(long accountNum, double amountToTransfer);

	public int withdraw(long accountNum, double amountToTransfer);

	public int transfer(long debitorAccNum, long creditorAccNum, double amountToTransfer);

	public ResultSet getUserData(long accountNum);
	
//	public ArrayList<Usertransaction> transactionHistory();

}
