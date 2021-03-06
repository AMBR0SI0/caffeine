/*
 * Copyright 2015 Ben Manes. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.benmanes.caffeine.cache.simulator.policy;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A cache that implements a page replacement policy.
 *
 * @author ben.manes@gmail.com (Ben Manes)
 */
public interface Policy {

  /** Records that the entry was accessed. */
  void record(AccessEvent event);

  /** Indicates that the recording has completed. */
  default void finished() {}

  /** Returns the cache efficiency statistics. */
  PolicyStats stats();

  /** The additional features supported. */
  enum Characteristic {
    WEIGHTED
  }

  /** An optional annotation to declare additional capabilities. */
  @Retention(RUNTIME)
  @Target(ElementType.TYPE)
  @interface PolicySpec {
    Characteristic[] characteristics();
  }

  /** A policy that does not exploit external event metadata. */
  interface KeyOnlyPolicy extends Policy {
    @Override default void record(AccessEvent event) {
      record(event.key());
    }
    void record(long key);
  }
}
