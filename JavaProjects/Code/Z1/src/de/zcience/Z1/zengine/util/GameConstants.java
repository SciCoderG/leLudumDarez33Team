package de.zcience.Z1.zengine.util;

import java.nio.file.AccessDeniedException;

public class GameConstants {
	// system priorities
	public static final int INPUT_PRIORITY = 10;
	public static final int PHYSICS_PRIORITY = 40;
	public static final int CAMERA_PRIORITY = 50;

	// Box2D constants
	public static final int BOX2D_VELOCITY_ITERATIONS = 8;
	public static final int BOX2D_POSITIONS_ITERATIONS = 3;
	public static final float BOX2D_SCALE = 64.0f;

	// CameraConstants
	public static final boolean YDOWN = false;

	// 
	private static int TileSizeX = 0;
	private static int TileSizeY = 0;

	public static int getTileSizeX() {
		return TileSizeX;
	}

	public static int getTileSizeY() {
		return TileSizeY;
	}

	public static void setTileSizeX(int x) throws AccessDeniedException {
		if (TileSizeX != 0)
			throw new AccessDeniedException(
					"Do not change Tilesize after initializing");
		else
			TileSizeX = x;
	}

	public static void setTileSizeY(int y) throws AccessDeniedException {
		if (TileSizeY != 0)
			throw new AccessDeniedException(
					"Do not change Tilesize after initializing");
		else
			TileSizeY = y;
	}
}
