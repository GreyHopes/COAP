package utils.requests;

import utils.CoAPMessage;

public abstract class CoAPRequest extends CoAPMessage
{
     public CoAPRequest()
     {
          codeClass = 0;
     }
}
