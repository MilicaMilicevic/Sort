package src.protocol;

import java.util.Random;

public class Protocol {
  private static String[] actions={"101IR","101IO","102ZR","102ZO","103AK","104EX"};
  
  public static String getRandom(){
    return actions[new Random().nextInt(actions.length)];
  }
}
  