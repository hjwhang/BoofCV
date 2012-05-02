/*
 * Copyright (c) 2011-2012, Peter Abeles. All Rights Reserved.
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

package boofcv.alg.feature.disparity;

import boofcv.struct.image.ImageUInt8;

/**
 * <p>
 * Implementation of {@link SelectRectStandardBase_S32} for {@link ImageUInt8}.
 * </p>
 *
 * @author Peter Abeles
 */
public class SelectRectStandard_S32_U8 extends SelectRectStandardBase_S32<ImageUInt8>
{
	public SelectRectStandard_S32_U8(int maxError, int rightToLeftTolerance, double texture) {
		super(maxError, rightToLeftTolerance, texture);
	}

	protected void setDisparity( int index , int value ) {
		imageDisparity.data[index] = (byte)value;
	}

	@Override
	public Class<ImageUInt8> getDisparityType() {
		return ImageUInt8.class;
	}
}
