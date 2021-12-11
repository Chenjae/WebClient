package com.mycompany.webapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class PagerAndBoards {
	private Pager pager;
	private List<Board> boards;
}
