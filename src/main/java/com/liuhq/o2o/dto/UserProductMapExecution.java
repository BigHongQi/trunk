package com.liuhq.o2o.dto;

import java.util.List;

import com.liuhq.o2o.entity.UserProductMap;

public class UserProductMapExecution {
	  
		private int count;
		
		private UserProductMap userProductMap;
		
		private List<UserProductMap> userProductMapList;
		
		public UserProductMapExecution() {
		}

		// 成功的构造器
		public UserProductMapExecution(int count,List<UserProductMap> list) {
			this.count = count;
			this.userProductMapList = list;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public UserProductMap getUserProductMap() {
			return userProductMap;
		}

		public void setUserProductMap(UserProductMap userProductMap) {
			this.userProductMap = userProductMap;
		}

	
		public List<UserProductMap> getUserProductMapList() {
			return userProductMapList;
		}

		public void setUserProductMapList(List<UserProductMap> userProductMapList) {
			this.userProductMapList = userProductMapList;
		}

		@Override
		public String toString() {
			return "UserProductMapExecution [count=" + count + ", userProductMap=" + userProductMap
					+ ", userProductMapList=" + userProductMapList + "]";
		}
      
	
}
