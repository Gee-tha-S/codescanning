import requests
import json
import os

# Fetch GitHub Personal Access Token from environment variable
#TOKEN = "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"
TOKEN = os.getenv("TOKEN-github")
print(TOKEN),
ORG_NAME = "FedEx"
params = {'per_page': 100}
alerts = []
if not TOKEN:
    print("TOKEN is not set.")
    exit(1)


def fetch_code_scanning_alerts(org, token):
    print("Fetching code scanning alerts data...")
    headers = {
        "Authorization": f"token {token}",
        #        "Accept": "application/vnd.github+json",
    }
    #url = f"https://api.github.com/orgs/{org}/code-scanning/alerts"
    url = f"https://api.github.com/repos/Gee-tha-S/codeqlexample/code-scanning/alerts"
    #url = f"https://api.github.com/repos/FedEx/eai-3535171-self-service-demo/code-scanning/alerts/1"
    #url = f"https://api.github.com/repos/FedEx/eai-3539454-smac/code-scanning/alerts?branch=FB-RefStaticCodeApr24CL-2730"
    page = 1
    try:
        while True:
            response = requests.get(url, headers=headers, params=params)
            print(response)
            response_json = response.json()
            if response.status_code == 200:
                alerts.extend(response_json)
                # check for next page
                if 'next' in response.links:
                    url = response.links['next']['url']
                else:
                    break
            else:
                print(f"Failed to fetch code scanning alerts data : HTTP {response.status_code} - {response.text}")
            break
        page += 1
    except Exception as e:
        print(f"Error during API call: {e}")
    return alerts


def main():
    alert_data = fetch_code_scanning_alerts(ORG_NAME, TOKEN)
    if alert_data:
        with open('code-scanning-alerts.json', 'w') as f:
            json.dump(alert_data, f, indent=4)
    else:
        print("Failed to fetch code-scanning alerts data or alerts data is empty.")


if __name__ == "__main__":
    main()
