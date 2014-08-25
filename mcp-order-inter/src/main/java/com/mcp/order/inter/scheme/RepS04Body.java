package com.mcp.order.inter.scheme;

import com.mcp.order.inter.RepBody;
import com.mcp.scheme.model.SchemeHm;

public class RepS04Body extends RepBody {

    private SchemeHm scheme;

    public SchemeHm getScheme() {
        return scheme;
    }

    public void setScheme(SchemeHm scheme) {
        this.scheme = scheme;
    }
}
