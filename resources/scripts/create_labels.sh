#!/usr/bin/env bash

# Colours picked from https://robinpowered.com/blog/best-practice-system-for-organizing-and-tagging-github-issues/

###
# Label definitions
###
declare -A LABELS

# Problems
LABELS["bug"]="EE3F46"

# Size
LABELS["large size"]="FFC274"
LABELS["medium size"]="FFC274"
LABELS["small size"]="FFC274"

# Additions
LABELS["feature"]="91CA55"
LABELS["epic"]="91CA55"
LABELS["user story"]="91CA55"

# Runway
LABELS["arch runway"]="eb6420"

# Inactive
LABELS["wontfix"]="D2DAE1"
LABELS["duplicate"]="D2DAE1"

# Value
LABELS["high value"]="0052cc"
LABELS["medium value"]="0052cc"
LABELS["low value"]="0052cc"

# Stage
LABELS["in design"]="006b75"
LABELS["dev ready"]="006b75"
LABELS["in dev"]="006b75"
LABELS["in test"]="006b75"
LABELS["test ready"]="006b75"
LABELS["in test"]="006b75"
LABELS["deploy ready"]="006b75"

###
# Get a token from Github
###
if [ ! -f ".token" ]; then
  read -p "Please enter your Github username: " user
  read -p "Please enter your 6 digit two-factor-authentication code: " otp_code

  curl -u "$user" -H "X-Github-OTP: $otp_code" -d '{"scopes":["repo", "public_repo"], "note":"Creating Labels"}' "https://api.github.com/authorizations" | jq -r '.token' > .token
fi

TOKEN=$(cat .token)

read -p "Who owns the repo you want labels on?: " owner
read -p "What repo do you want labels on?: " repo

for K in "${!LABELS[@]}"; do
  CURL_OUTPUT=$(curl -s -H "Authorization: token $TOKEN" -X POST "https://api.github.com/repos/$owner/$repo/labels" -d "{\"name\":\"$K\", \"color\":\"${LABELS[$K]}\"}")
  HAS_ERROR=$(echo "$CURL_OUTPUT" | jq -r '.errors')
  if [[ (! -z "$HAS_ERROR") && !("$HAS_ERROR" == "null") ]]; then
    ERROR=$(echo "$CURL_OUTPUT" | jq -r '.errors[0].code')

    if [ "$ERROR" == "already_exists" ]; then
      # We update
      echo "'$K' already exists. Updating..."
      CURL_OUTPUT=$(curl -s -H "Authorization: token $TOKEN" -X PATCH "https://api.github.com/repos/$owner/$repo/labels/${K/ /%20}" -d "{\"name\":\"$K\", \"color\":\"${LABELS[$K]}\"}")
    else
      echo "Unknown error: $ERROR"
      echo "Output from curl: "
      echo "$CURL_OUTPUT"
      echo "Exiting..."
      exit;
    fi
  else
    echo "Created '$K'."
  fi
done
