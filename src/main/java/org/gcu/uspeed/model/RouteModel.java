package org.gcu.uspeed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RouteModel {

	private int id;
	private String trackedRoute;
	private int user_id;

}
