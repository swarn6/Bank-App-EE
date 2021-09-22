package com.bank.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bank.user.bean.UserDetails;

public class UserDaoImpl implements UserDao {
	static int acc_no = 1001;

	public static Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// conn=DriverManager.getConnection(driver_value); //configure driver_value in
			// .xml
		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;

	}

	public int createAccount(UserDetails user) {
		int status = 0;
		if (user.getBalance() < 5000) {
			return -1;
			// check in jsp if status==-1 then Sorry min bal 5000 allowed
		}
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("insert into accounts values(?,?,?,?,?,?)");
			ps.setInt(1, ++acc_no);
			ps.setString(2, user.getName());
			ps.setString(3, user.getBankBranch());
			ps.setString(4, user.getAccountType());
			ps.setDouble(5, user.getBalance());
			ps.setLong(6, user.getContactNumber());
			status = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
		// if status > 0 then success message if status = 0 then connection error
	}

	public int deposit(long accountNum, double amountToTransfer) {
		int status = 0;
		try {
			Connection conn = getConnection();

			PreparedStatement stmt = conn.prepareStatement("select balance from accounts where acc_no = ?");
			stmt.setLong(1, accountNum);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				double balance = result.getDouble("balance");
				// This function 'userData' is a hashmap <acc_no, UserDetails> retrieves all
				// User details
				balance += amountToTransfer;
				stmt = conn.prepareStatement("update accounts set balance = ? where acc_no = ?");
				stmt.setLong(1, accountNum);
				stmt.setDouble(5, balance);
				status = stmt.executeUpdate();

//				UserTransaction fundTransfer = new UserTransaction(transactionId++, accountNum, 0, amountToTransfer, new Date(),
//						finalBalance, "Deposit");
//				transaction.put(fundTransfer, accountNum); 
//			ADDITION to user transaction history

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return status;
		// check in jsp page if status!=0 then success else invalid account number on
		// the screen
	}

	public int withdraw(long accountNum, double amountToTransfer) {
		int status = 0;
		try {
			Connection conn = getConnection();

			PreparedStatement stmt = conn.prepareStatement("select balance from accounts where acc_no = ?");
			stmt.setLong(1, accountNum);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				double balance = result.getDouble("balance");
				if (balance > amountToTransfer) {
					balance -= amountToTransfer;
					stmt = conn.prepareStatement("update accounts set balance = ? where acc_no = ?");
					stmt.setLong(1, accountNum);
					stmt.setDouble(5, balance);
					status = stmt.executeUpdate();
				} else {
					return -1;
					// check in jsp if -1 then low balance
				}
			} else {
				return -2;
				// check in jsp if -1 then invalid acc no
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return status;
		// check in jsp page if status > 0 then success else invalid account number on
		// the screen
		// check in jsp if status = -1 then low balance
	}

	public int transfer(long debitorAccNum, long creditorAccNum, double amountToTransfer) {
		int status = 0;
		try {
			Connection conn = getConnection();

			PreparedStatement stmt = conn.prepareStatement("select balance from accounts where acc_no = ?");
			stmt.setLong(1, debitorAccNum);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				double deb_balance = result.getDouble("balance");
				if (deb_balance < amountToTransfer) {
					return -1;
					// check in jsp if -1 then low balance
				} else {
					stmt = conn.prepareStatement("select balance from accounts where acc_no = ?");
					stmt.setLong(1, creditorAccNum);
					ResultSet res = stmt.executeQuery();
					if (res.next()) {
						double cred_balance = result.getDouble("balance");
						deb_balance -= amountToTransfer;
						cred_balance += amountToTransfer;
						stmt = conn.prepareStatement("update accounts set balance = ? where acc_no = ?");
						stmt.setLong(1, debitorAccNum);
						stmt.setDouble(5, deb_balance);
						stmt.executeUpdate();
						stmt = conn.prepareStatement("update accounts set balance = ? where acc_no = ?");
						stmt.setLong(1, creditorAccNum);
						stmt.setDouble(5, cred_balance);
						status = stmt.executeUpdate();
					} else {
						return -2;
						// check in jsp if -2 then creditor acc no wrong
					}
				}
			} else {
				return -3;
				//// check in jsp if -3 then debitor acc no wrong
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return status;
		// check in jsp if status>0 then success
		// if status = 0 then database connection error
	}

	public ResultSet getUserData(long accountNum) {

		ResultSet result = null;

		try {
			Connection con = getConnection();
			PreparedStatement stmt = con.prepareStatement("Select * from users where acc_no = ?");
			stmt.setLong(1, accountNum);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {

				return res; // orward details to jsp

//				long acc_no = result.getLong("acc_no");
//				String name = result.getString("name");
//				String branch = result.getString("branch");
//				String type = result.getString("acc_type");
//				double balance = result.getDouble("balance");
//				long contact = result.getLong("contact");

				// 

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

//	public ArrayList<Usertransaction> transactionHistory() {
//		Set<Usertransaction> transactionsDone = transaction.keySet();
//		ArrayList<Usertransaction> list = new ArrayList<Usertransaction>();
//		list.addAll(transactionsDone);
//		return list;
//	make transactionHistory function available in the corresponding jsp}
}
