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

PLAYER_ARTWORK = \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/visuals/logo.jpg \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/visuals/play.png \
$(PLAYER_DIR)$(OUTPUT_DIR)/clientplayer/visuals/pause.png \

$(PLAYER_DIR)$(OUTPUT_DIR)/%.jpg: $(PLAYER_DIR)$(INPUT_DIR)/%.jpg
	@echo "Copying artwork: $<"
	@$(CP) $(PLAYER_DIR)$(INPUT_DIR)/$*.jpg $(PLAYER_DIR)$(OUTPUT_DIR)/$*.jpg

$(PLAYER_DIR)$(OUTPUT_DIR)/%.png: $(PLAYER_DIR)$(INPUT_DIR)/%.png
	@echo "Copying artwork: $<"
	@$(CP) $(PLAYER_DIR)$(INPUT_DIR)/$*.png $(PLAYER_DIR)$(OUTPUT_DIR)/$*.png

$(PLAYER_DIR)$(OUTPUT_DIR)/%.class: $(PLAYER_DIR)$(INPUT_DIR)/%.java
	@echo ""
	@echo "PLAYER"
	@echo "------"
	@echo "Creating output folder: $(PLAYER_DIR)$(OUTPUT_DIR)"
	@$(MD) $(PLAYER_DIR)$(OUTPUT_DIR)
	@echo "Compiling JukePi player"
	@$(JC) $(JFLAGS) -d $(PLAYER_DIR)$(OUTPUT_DIR) -cp $(NETWORK_DIR)$(OUTPUT_DIR) $(PLAYER_SRC_FILES)
