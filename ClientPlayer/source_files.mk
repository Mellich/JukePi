PLAYER_SRC_FILES = \
$(PLAYER_DIR)$(INPUT_DIR)/utilities/IO.java \
$(PLAYER_DIR)$(INPUT_DIR)/utilities/ProcessCommunicator.java \
$(PLAYER_DIR)$(INPUT_DIR)/clientplayer/Status.java \
$(PLAYER_DIR)$(INPUT_DIR)/clientplayer/visuals/Visualizer.java \
$(PLAYER_DIR)$(INPUT_DIR)/clientplayer/visuals/IdleViewer.java \
$(PLAYER_DIR)$(INPUT_DIR)/clientplayer/player/VLCPlayerFactory.java \
$(PLAYER_DIR)$(INPUT_DIR)/clientplayer/player/VLCPlayer.java \
$(PLAYER_DIR)$(INPUT_DIR)/clientplayer/player/PlayerFactory.java \
$(PLAYER_DIR)$(INPUT_DIR)/clientplayer/player/OMXPlayerFactory.java \
$(PLAYER_DIR)$(INPUT_DIR)/clientplayer/player/OMXPlayer.java \
$(PLAYER_DIR)$(INPUT_DIR)/clientplayer/player/Player.java \
$(PLAYER_DIR)$(INPUT_DIR)/clientplayer/PlayerStarter.java \
$(PLAYER_DIR)$(INPUT_DIR)/clientplayer/BroadcastListener.java

PLAYER_CLASS_FILES = \
$(PLAYER_DIR)$(OUTPUT_DIR)/utilities/IO.class \
$(PLAYER_DIR)$(OUTPUT_DIR)/utilities/ProcessCommunicator.class \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/Status.class \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/visuals/Visualizer.class \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/visuals/IdleViewer.class \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/player/VLCPlayerFactory.class \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/player/VLCPlayer.class \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/player/PlayerFactory.class \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/player/OMXPlayerFactory.class \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/player/OMXPlayer.class \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/player/Player.class \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/PlayerStarter.class \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/BroadcastListener.class

$(PLAYER_DIR)$(OUTPUT_DIR)/%.class: $(PLAYER_DIR)$(INPUT_DIR)/%.java
	@echo "Making JukePi Player..."
	$(MD) $(PLAYER_DIR)$(OUTPUT_DIR)
	$(JC) $(JFLAGS) -d $(PLAYER_DIR)$(OUTPUT_DIR) -cp $(NETWORK_DIR)$(OUTPUT_DIR) $(PLAYER_SRC_FILES)
