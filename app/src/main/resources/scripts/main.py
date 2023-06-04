import time
from blinkpy.auth import Auth
from blinkpy.blinkpy import Blink
import sys


def photoscript(param):

    blink = Blink()

    if blink.check_if_ok_to_update():

        # Can set no_prompt when initializing auth handler
        auth = Auth({"username": "[USERMAME]", "password": "[PASSWORD]]"}, no_prompt=True)
        blink.auth = auth
        blink.start()

        //if you are prompted with a 2FA code, you can put it right here
        auth.send_auth_key(blink, "[2FA CODE]
        blink.setup_post_verify()

        camera = blink.cameras['[CAMERA TYPE]]']
        camera.snap_picture()
        time.sleep(5)
        blink.refresh()
        time.sleep(5)
        camera.image_to_file(f'./pictures/{param}.jpg')
        # camera.video_to_file('./video.mp4')


if __name__ == '__main__':

    photoscript(sys.argv[1])
