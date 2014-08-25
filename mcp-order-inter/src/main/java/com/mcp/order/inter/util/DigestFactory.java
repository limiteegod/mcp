package com.mcp.order.inter.util;

import com.mcp.core.util.Base64;
import com.mcp.core.util.DesUtil;
import com.mcp.core.util.MD5Util;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.Head;
import org.apache.log4j.Logger;

/**
 * Created by ming.li on 2014/7/1.
 */
public class DigestFactory {

    private static Logger log = Logger.getLogger(DigestFactory.class);

    /**
     * 校验加密，如果检验失败，则返回null
     * @param h
     * @param body
     * @return
     */
    public static String check(Head h, String body, String key)
    {
        String repBody = null;
        String digestType = h.getDigestType();
        if(digestType.equals(Constants.GATEWAY_DIGEST_TYPE_MD5))
        {
            String digestStr = body + h.getTimestamp() + key;
            String md5 = MD5Util.MD5(digestStr);
            if(md5.equals(h.getDigest()))
            {
                repBody =  body;
            }
            else
            {
                log.error("system md5:" + md5 + ",outer md5:" + h.getDigest());
                log.error("system body:" + digestStr);
            }
        }
        else if(digestType.equals(Constants.GATEWAY_DIGEST_TYPE_DES))
        {
            final byte[] keyBytes = Base64.decode(key);
            try {
                byte[] srcBytes = DesUtil.decryptMode(keyBytes, Base64.decode(body));
                repBody = new String(srcBytes, "utf-8");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(digestType.equals(Constants.GATEWAY_DIGEST_TYPE_3DES))
        {
            final byte[] keyBytes = Base64.decode(key);
            try {
                byte[] srcBytes = DesUtil.decryptTrippleMode(keyBytes, Base64.decode(body));
                repBody = new String(srcBytes, "utf-8");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return repBody;
    }

    /**
     * 生成加密的字符串
     * @param h
     * @param body
     * @param key
     * @return
     */
    public static String generate(Head h, String body, String key)
    {
        String repBody = null;
        String digestType = h.getDigestType();
        if(digestType.equals(Constants.GATEWAY_DIGEST_TYPE_MD5))
        {
            String digestStr = body + h.getTimestamp() + key;
            String md5 = MD5Util.MD5(digestStr);
            h.setDigest(md5);
        }
        else if(digestType.equals(Constants.GATEWAY_DIGEST_TYPE_DES))
        {
            final byte[] keyBytes = Base64.decode(key);
            try
            {
                repBody = Base64.encode(DesUtil.encryptMode(keyBytes, body.getBytes("utf-8")));
            }
            catch (Exception e)
            {
                throw new CoreException(ErrCode.E0001, ErrCode.codeToMsg(ErrCode.E0001));
            }
        }
        else if(digestType.equals(Constants.GATEWAY_DIGEST_TYPE_3DES))
        {
            final byte[] keyBytes = Base64.decode(key);
            try
            {
                repBody = Base64.encode(DesUtil.encryptTrippleMode(keyBytes, body.getBytes("utf-8")));
            }
            catch (Exception e)
            {
                throw new CoreException(ErrCode.E0001, ErrCode.codeToMsg(ErrCode.E0001));
            }
        }
        return repBody;
    }
}
