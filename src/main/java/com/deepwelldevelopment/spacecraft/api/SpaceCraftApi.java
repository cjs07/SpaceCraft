package com.deepwelldevelopment.spacecraft.api;

import com.deepwelldevelopment.spacecraft.api.internal.DummyInternalMethodHandler;
import com.deepwelldevelopment.spacecraft.api.internal.IInternalMethodHandler;

public class SpaceCraftApi {

    public static IInternalMethodHandler internalMethods = new DummyInternalMethodHandler();
}
