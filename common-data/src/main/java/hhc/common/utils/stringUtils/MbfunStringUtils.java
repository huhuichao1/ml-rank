package hhc.common.utils.stringUtils;

public class MbfunStringUtils {

	/**
	 * 统一大小写
	 * @param str
	 * @return
	 */
	public static String toLowerCase(String str)
	{
		return str.toLowerCase();
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
    public static boolean isEmpty(String str) {  
        return str == null || "".equals(str);  
    }  
  
    /** 
     * 全角转半角: 
     * @param fullStr 
     * @return 
     */  
    public static final String full2Half(String fullStr) {  
        if(isEmpty(fullStr)){  
            return fullStr;  
        }  
        char[] c = fullStr.toCharArray();  
        for (int i = 0; i < c.length; i++) {  
            if (c[i] >= 65281 && c[i] <= 65374) {  
                c[i] = (char) (c[i] - 65248);  
            } else if (c[i] == 12288) { // 空格  
                c[i] = (char) 32;  
            }  
        }  
        return new String(c);  
    } 
    
    /**
     * 
     */
    
    public static final String replaceSpecailWord(String input)
    {
    	if(input == null)
    	{
    		return null;
    	}
    	char[] cl = input.toCharArray();  
    	for(int index =0;index<input.length();index++)
    	{
    		char c = cl[index];
    		if( c < 127)
    		{
    			if('0' <= c && '9' >= c
    			|| 'a' <= c && 'z' >= c)
    			{
    			}
    			else
    			{
    				cl[index] = ' ';
    			}
    		}
    	}
    	return (new String(cl)).trim();  
    }
    
  
    public static void main(String[] arg)
    {
    	System.out.println(MbfunStringUtils.replaceSpecailWord("上海人民光*777(+/?-"));
    }

}