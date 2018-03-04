package org.jay;

import java.util.Date;
import java.util.List;

public class Transaction {
	private Integer seq;
	private String market_name;
	private Date approval_date;
	private String member_name;
	private String team_name;
	private List<Item> items;
	private String approval_authority;
	private Integer price;
	private String approval_group;
	private Double approval_number;
	
	public Integer getSeq() {
		return seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public String getMarket_name() {
		return market_name;
	}
	
	public void setMarket_name(String market_name) {
		this.market_name = market_name;
	}
	
	public Date getApproval_date() {
		return approval_date;
	}
	
	public void setApproval_date(Date approval_date) {
		this.approval_date = approval_date;
	}
	
	public String getMember_name() {
		return member_name;
	}
	
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	
	public String getTeam_name() {
		return team_name;
	}
	
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public String getApproval_authority() {
		return approval_authority;
	}
	
	public void setApproval_authority(String approval_authority) {
		this.approval_authority = approval_authority;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public String getApproval_group() {
		return approval_group;
	}
	
	public void setApproval_group(String approval_group) {
		this.approval_group = approval_group;
	}
	
	public Double getApproval_number() {
		return approval_number;
	}
	
	public void setApproval_number(Double approval_number) {
		this.approval_number = approval_number;
	}
}
