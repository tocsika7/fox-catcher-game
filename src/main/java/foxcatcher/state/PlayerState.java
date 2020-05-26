package foxcatcher.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *  A class representing the state of the players.
 */
@Data
public class PlayerState {

    /**
     * A value that stores the steps of the fox.
     */
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private int p1Steps = 0;

    /**
     * A value that stores the steps of the dogs.
     */
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private int p2Steps = 0;

    /**
     *  Value that stores if the currently selected character is the dog.
     */
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private boolean dogSelected = false;

    /**
     *  Value that stores if the currently selected character is the fox.
     */
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private boolean foxSelected = false;

    /**
     *  A method that sets either one of the selected characters.
     * @param player Which players character should be set.
     * @param state  What the characters state should be.
     */
    public void setSelectedCharacter(int player, boolean state){
        if(player==1)
            foxSelected=state;
        if(player==2)
            dogSelected=state;
    }

}
