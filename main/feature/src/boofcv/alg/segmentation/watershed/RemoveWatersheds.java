/*
 * Copyright (c) 2011-2014, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package boofcv.alg.segmentation.watershed;

import boofcv.struct.image.ImageSInt32;
import org.ddogleg.struct.GrowQueue_I32;

/**
 * Examines a segmented image created by {@link WatershedVincentSoille1991} and removes segmentation watershed pixels.
 *
 * @author Peter Abeles
 */
public class RemoveWatersheds {

	int connect[] = new int[4];

	GrowQueue_I32 open = new GrowQueue_I32();
	GrowQueue_I32 open2 = new GrowQueue_I32();

	/**
	 * Removes watersheds from the segmented image.  The inputed image should be the entire original
	 * segmented image and assumes the outside border is filled with values < 0.  Each watershed is
	 * assigned the value of an arbitrary neighbor.  4-connect rule is used for neighbors.  Doesn't matter
	 * if initial segmented was done using another connectivity rule.  The value of each region is reduced
	 * by one at the very end.
	 *
	 * @param segmented Entire segmented image with watersheds
	 */
	public void remove( ImageSInt32 segmented ) {
		open.reset();

		connect[0] = -1;
		connect[1] =  1;
		connect[2] = segmented.stride;
		connect[3] = -segmented.stride;

		// step through the inner pixels and find watershed pixels
		for( int y = 1; y < segmented.height-1; y++ ) {
			int index = y*segmented.stride + 1;
			for( int x = 1; x < segmented.width-1; x++ , index++ ) {
				if( segmented.data[index] == 0 ) {
					open.add( index );
				}
			}
		}

		// assign region values to watersheds until they are all assigned
		while( open.size != 0 ) {
			open2.reset();
			for( int i = 0; i < open.size; i++ ) {
				int index = open.get(i);
				// assign it to the first valid region it finds
				for( int j = 0; j < 4; j++ ) {
					// the outside border in the enlarged segmented image will have -1 and watersheds are 0
					int r =  segmented.data[index+connect[j]];
					if( r > 0 ) {
						segmented.data[index] = r;
						break;
					}
				}

				// see if it was not assigned a region
				if( segmented.data[index] == 0 ) {
					open2.add(index);
				}
			}

			// swap open and open2
			GrowQueue_I32 tmp = open;
			open = open2;
			open2 = tmp;
		}

		// watershed pixels have a value of 0 and have been removed. So change the region ID numbers by 1
		for( int y = 1; y < segmented.height-1; y++ ) {
			int index = y*segmented.stride + 1;
			for( int x = 1; x < segmented.width-1; x++ , index++ ) {
				segmented.data[index]--;
			}
		}
	}
}
