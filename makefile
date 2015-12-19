#
# define compiler and compiler flag variables
#

.SUFFIXES: .java .class

JFLAGS = -g:none -encoding latin1

JC = javac
MD = mkdir -p
RM = rm -f -r
CP = cp -f
EXE = chmod +x

OUTPUT_DIR= /bin
INPUT_DIR = /src
JAR_DIR = /jar

SOURCE_FILES = source_files.mk

CLIENT_DIR = DesktopClient
SERVER_DIR = YTMusicBox
PLAYER_DIR = ClientPlayer
NETWORK_DIR = Network

MANIFEST = Manifest.txt

#
# files in sub directories including needed source and class files
#
-include $(NETWORK_DIR)/$(SOURCE_FILES)
-include $(SERVER_DIR)/$(SOURCE_FILES)
-include $(CLIENT_DIR)/$(SOURCE_FILES)
-include $(PLAYER_DIR)/$(SOURCE_FILES)

#
# the default make target entry
#
all: Server.jar Player.jar Client.jar
	@echo "compilation done."

Network: $(NETWORK_CLASS_FILES)
	@echo "network compilation done."

Server: Network $(SERVER_CLASS_FILES)
	@echo "server compilation done."

Player: Network $(PLAYER_CLASS_FILES) $(PLAYER_ARTWORK)
	@echo "player compilation done."

Client: Server $(CLIENT_CLASS_FILES)
	@echo "client compilation done."

Server.jar: Server
	@echo "Creating executable JAR file for the JukePi server"
	@echo "Creating directory: $(SERVER_DIR)$(JAR_DIR)"
	@$(MD) $(SERVER_DIR)$(JAR_DIR)
	@echo "Packing JAR file..."
	@jar cfm $(SERVER_DIR)$(JAR_DIR)/Server.jar $(SERVER_DIR)/$(MANIFEST) -C $(NETWORK_DIR)$(OUTPUT_DIR) . -C $(SERVER_DIR)$(OUTPUT_DIR) .
	@echo "Making JAR file executable"
	@$(EXE) $(SERVER_DIR)$(JAR_DIR)/Server.jar
	@echo "Server created."
	@echo ""
	
Client.jar: Client
	@echo "Creating executable JAR file for the JukePi client"
	@echo "Creating directory: $(CLIENT_DIR)$(JAR_DIR)"
	@$(MD) $(CLIENT_DIR)$(JAR_DIR)
	@echo "Packing JAR file..."
	@jar cfm $(CLIENT_DIR)$(JAR_DIR)/Client.jar $(CLIENT_DIR)/$(MANIFEST) -C $(NETWORK_DIR)$(OUTPUT_DIR) . -C $(SERVER_DIR)$(OUTPUT_DIR) . -C $(CLIENT_DIR)$(OUTPUT_DIR) .
	@echo "Making JAR file executable"
	@$(EXE) $(CLIENT_DIR)$(JAR_DIR)/Client.jar
	@echo "Client created."
	@echo ""
	
Player.jar: Player
	@echo "Creating executable JAR file for the JukePi player"
	@echo "Creating directory: $(PLAYER_DIR)$(JAR_DIR)"
	@$(MD) $(PLAYER_DIR)$(JAR_DIR)
	@echo "Packing JAR file..."
	@jar cfm $(PLAYER_DIR)$(JAR_DIR)/Player.jar $(PLAYER_DIR)/$(MANIFEST) -C $(NETWORK_DIR)$(OUTPUT_DIR) . -C $(PLAYER_DIR)$(OUTPUT_DIR) .
	@echo "Making JAR file executable"
	@$(EXE) $(PLAYER_DIR)$(JAR_DIR)/Player.jar	
	@echo "Player created."
	@echo ""
#
# RM is a predefined macro in make (RM = rm -f)
#

clean:
	$(RM) $(SERVER_DIR)$(JAR_DIR)
	$(RM) $(SERVER_DIR)$(OUTPUT_DIR)
	$(RM) $(NETWORK_DIR)$(OUTPUT_DIR)
	$(RM) $(CLIENT_DIR)$(JAR_DIR)
	$(RM) $(CLIENT_DIR)$(OUTPUT_DIR)
	$(RM) $(PLAYER_DIR)$(JAR_DIR)
	$(RM) $(PLAYER_DIR)$(OUTPUT_DIR)
