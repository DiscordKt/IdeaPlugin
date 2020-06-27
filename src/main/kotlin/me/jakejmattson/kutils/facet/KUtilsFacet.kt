package me.jakejmattson.kutils.facet

import com.intellij.facet.*
import com.intellij.openapi.module.Module

class KUtilsFacet(facetType: FacetType<*, *>,
                  module: Module,
                  name: String,
                  configuration: KUtilsFacetConfiguration,
                  underlyingFacet: Facet<KUtilsFacetConfiguration>)
    : Facet<KUtilsFacetConfiguration?>(facetType, module, name, configuration, underlyingFacet)