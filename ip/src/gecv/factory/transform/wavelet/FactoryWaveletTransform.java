/*
 * Copyright 2011 Peter Abeles
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package gecv.factory.transform.wavelet;

import gecv.abst.wavelet.WaveletTransform;
import gecv.abst.wavelet.impl.WaveletTransformFloat32;
import gecv.abst.wavelet.impl.WaveletTransformInt;
import gecv.struct.image.ImageBase;
import gecv.struct.image.ImageFloat32;
import gecv.struct.image.ImageInteger;
import gecv.struct.image.ImageSInt32;
import gecv.struct.wavelet.WaveletDescription;
import gecv.struct.wavelet.WlCoef;
import gecv.struct.wavelet.WlCoef_F32;
import gecv.struct.wavelet.WlCoef_I32;


/**
 * Simplified factory for creating {@link gecv.abst.wavelet.WaveletTransform}.  Factories are provided
 * for creating the different wavelet descriptions.
 *
 * @author Peter Abeles
 */
public class FactoryWaveletTransform {


	@SuppressWarnings({"unchecked"})
	public static <T extends ImageBase, W extends ImageBase, C extends WlCoef>
	WaveletTransform<T,W,C> create( WaveletDescription<C> waveletDesc , int numLevels )
	{
		if( waveletDesc.getForward().getType() == float.class ) {
			return (WaveletTransform<T,W,C>)create_F32((WaveletDescription)waveletDesc,numLevels);
		} else if( waveletDesc.getForward().getType() == int.class ) {
			return (WaveletTransform<T,W,C>)create_I((WaveletDescription)waveletDesc,numLevels);
		} else {
			throw new RuntimeException("Add support for this image type");
		}
	}

	/**
	 * Creates a wavelet transform for images that are derived from {@link ImageInteger}.
	 *
	 * @param waveletDesc Description of the wavelet.
	 * @param numLevels Number of levels in the multi-level transform.
	 * @return The transform class.
	 */
	public static <T extends ImageInteger>
	WaveletTransform<T, ImageSInt32,WlCoef_I32>
	create_I( WaveletDescription<WlCoef_I32> waveletDesc ,
			  int numLevels )
	{
		return new WaveletTransformInt<T>(waveletDesc,numLevels);
	}

	/**
	 * Creates a wavelet transform for images that are of type {@link ImageFloat32}.
	 *
	 * @param waveletDesc Description of the wavelet.
	 * @param numLevels Number of levels in the multi-level transform.
	 * @return The transform class.
	 */
	public static
	WaveletTransform<ImageFloat32, ImageFloat32,WlCoef_F32>
	create_F32( WaveletDescription<WlCoef_F32> waveletDesc ,
				int numLevels )
	{
		return new WaveletTransformFloat32(waveletDesc,numLevels);
	}
}