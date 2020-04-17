/*
 *  ======== ti_drivers_config.h ========
 *  Configured TI-Drivers module declarations
 *
 *  DO NOT EDIT - This file is generated for the CC3235SF_LAUNCHXL
 *  by the SysConfig tool.
 */
#ifndef ti_drivers_config_h
#define ti_drivers_config_h

#define CONFIG_SYSCONFIG_PREVIEW

#define CONFIG_CC3235SF_LAUNCHXL

#ifndef DeviceFamily_CC3220
#define DeviceFamily_CC3220
#endif

#include <ti/devices/DeviceFamily.h>

#include <stdint.h>

/* support C++ sources */
#ifdef __cplusplus
extern "C" {
#endif


/*
 *  ======== GPIO ========
 */

/* P03 */
#define CONFIG_GPIO_OUT1            0
/* P05 */
#define CONFIG_GPIO_OUT2            1
/* P06 */
#define CONFIG_GPIO_IN              2
/* P64, LaunchPad Blue LED */
#define CONFIG_LED_0_GPIO           3
/* P02, LaunchPad Green LED */
#define CONFIG_LED_1_GPIO           4
/* P04, LaunchPad Button SW2 (Left) */
#define CONFIG_GPIO_0               5
/* P15, LaunchPad User Button SW3 (Right) */
#define CONFIG_GPIO_1               6

/* LEDs are active high */
#define CONFIG_GPIO_LED_ON  (1)
#define CONFIG_GPIO_LED_OFF (0)

#define CONFIG_LED_ON  (CONFIG_GPIO_LED_ON)
#define CONFIG_LED_OFF (CONFIG_GPIO_LED_OFF)


/*
 *  ======== PWM ========
 */

/* P1, LaunchPad Red LED */
#define CONFIG_LED_2_PWM            0


/*
 *  ======== Timer ========
 */

#define CONFIG_TIMER_0              0

/*
 *  ======== UART ========
 */

/*
 *  TX: P55
 *  RX: P57
 *  XDS110 UART
 */
#define CONFIG_UART_0               0


/*
 *  ======== Button ========
 */

/* P04, LaunchPad Button SW2 (Left) */
#define CONFIG_BUTTON_0             0
/* P15, LaunchPad User Button SW3 (Right) */
#define CONFIG_BUTTON_1             1


/*
 *  ======== LED ========
 */

/* P64, LaunchPad Blue LED */
#define CONFIG_LED_0                0
/* P02, LaunchPad Green LED */
#define CONFIG_LED_1                1
/* P1, LaunchPad Red LED */
#define CONFIG_LED_2                2


/*
 *  ======== Board_init ========
 *  Perform all required TI-Drivers initialization
 *
 *  This function should be called once at a point before any use of
 *  TI-Drivers.
 */
extern void Board_init(void);

/*
 *  ======== Board_initGeneral ========
 *  (deprecated)
 *
 *  Board_initGeneral() is defined purely for backward compatibility.
 *
 *  All new code should use Board_init() to do any required TI-Drivers
 *  initialization _and_ use <Driver>_init() for only where specific drivers
 *  are explicitly referenced by the application.  <Driver>_init() functions
 *  are idempotent.
 */
#define Board_initGeneral Board_init

#ifdef __cplusplus
}
#endif

#endif /* include guard */
