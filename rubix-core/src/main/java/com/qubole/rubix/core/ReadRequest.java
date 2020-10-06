/**
 * Copyright (c) 2019. Qubole Inc
 * Licensed under the Apache License, Version 2.0 (the License);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an AS IS BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. See accompanying LICENSE file.
 */
package com.qubole.rubix.core;

import java.util.Arrays;
import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created by stagra on 4/1/16.
 */
public class ReadRequest
{
  /*
   * Encapsulates two forms of request
   * 1. The actual request that client had made, this might not be block aligned
   * 2. The backend request that will be generated from acutal request after aligning it to block boundaries
   *
   * All ends i.e actualReadEnd, backendReadEnd are exclusive and that byte will not be read
   */
  long backendReadStart;
  long backendReadEnd;

  long actualReadStart;
  long actualReadEnd;

  byte[] destBuffer;
  int destBufferOffset;

  long backendFileSize;

  public ReadRequest() {};

  public ReadRequest(long backendReadStart, long backendReadEnd, long actualReadStart, long actualReadEnd, byte[] destBuffer, int destBufferOffset, long backendFileSize)
  {
    this.backendReadStart = backendReadStart;
    this.backendReadEnd = backendReadEnd;
    this.actualReadStart = actualReadStart;
    this.actualReadEnd = actualReadEnd;
    this.destBuffer = destBuffer;
    this.destBufferOffset = destBufferOffset;
    this.backendFileSize = backendFileSize;
  }

  public long getBackendReadStart()
  {
    return backendReadStart;
  }

  public void setBackendReadStart(long backendReadStart)
  {
    this.backendReadStart = backendReadStart;
  }

  public long getBackendReadEnd()
  {
    return backendReadEnd;
  }

  public void setBackendReadEnd(long backendReadEnd)
  {
    this.backendReadEnd = backendReadEnd;
  }

  public long getActualReadStart()
  {
    return actualReadStart;
  }

  public void setActualReadStart(long actualReadStart)
  {
    this.actualReadStart = actualReadStart;
  }

  public long getActualReadEnd()
  {
    return actualReadEnd;
  }

  public void setActualReadEnd(long actualReadEnd)
  {
    this.actualReadEnd = actualReadEnd;
  }

  public byte[] getDestBuffer()
  {
    return destBuffer;
  }

  public void setDestBuffer(byte[] destBuffer)
  {
    this.destBuffer = destBuffer;
  }

  public int getDestBufferOffset()
  {
    return destBufferOffset;
  }

  public void setDestBufferOffset(int destBufferOffset)
  {
    this.destBufferOffset = destBufferOffset;
  }

  public long getBackendFileSize()
  {
    return backendFileSize;
  }

  public void setBackendFileSize(long backendFileSize)
  {
    this.backendFileSize = backendFileSize;
  }

  // Use this method only when caller is assured of no integer overflows
  public int getActualReadLengthIntUnsafe()
  {
    return Math.toIntExact(actualReadEnd - actualReadStart);
  }

  // Use this method only when caller is assured of no integer overflows
  public int getBackendReadLengthIntUnsafe()
  {
    return Math.toIntExact(backendReadEnd - backendReadStart);
  }

  public long getActualReadLength()
  {
    return actualReadEnd - actualReadStart;
  }

  public long getBackendReadLength()
  {
    return backendReadEnd - backendReadStart;
  }


  public ReadRequest clone(boolean createNewBuffer)
  {
    ReadRequest otherRequest = new ReadRequest();
    otherRequest.backendReadStart = this.backendReadStart;
    otherRequest.backendReadEnd = this.backendReadEnd;
    otherRequest.actualReadStart = this.actualReadStart;
    otherRequest.actualReadEnd = this.actualReadEnd;
    if (createNewBuffer) {
      otherRequest.destBuffer = Arrays.copyOf(this.destBuffer, this.destBuffer.length);
    }
    else {
      otherRequest.destBuffer = this.destBuffer;
    }
    otherRequest.destBufferOffset = this.destBufferOffset;
    otherRequest.backendFileSize = this.backendFileSize;

    return otherRequest;
  }

  @Override
  public boolean equals (Object o)
  {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ReadRequest that = (ReadRequest) o;

    return backendReadStart == that.backendReadStart &&
            backendReadEnd == that.backendReadEnd &&
            actualReadStart == that.actualReadStart &&
            actualReadEnd == that.actualReadEnd &&
            destBufferOffset == that.destBufferOffset &&
            backendFileSize == that.backendFileSize &&
            Arrays.equals(destBuffer, that.destBuffer);
  }

  @Override
  public int hashCode ()
  {
    int result = Objects.hash(backendReadStart, backendReadEnd, actualReadStart, actualReadEnd, destBufferOffset, backendFileSize);
    result = 31 * result + Arrays.hashCode(destBuffer);
    return result;
  }

  @Override
  public String toString ()
  {
    return toStringHelper(this)
            .add("backendReadStart", backendReadStart)
            .add("backendReadEnd", backendReadEnd)
            .add("actualReadStart", actualReadStart)
            .add("actualReadEnd", actualReadEnd)
            .add("destBuffer", destBuffer)
            .add("destBufferOffset", destBufferOffset)
            .add("backendFileSize", backendFileSize)
            .toString();
  }
}
